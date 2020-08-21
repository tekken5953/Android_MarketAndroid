package app.marketandroid.Manager;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.marketandroid.R;
import app.marketandroid.Retrofit.DemandItem;
import app.marketandroid.Retrofit.DemandItem_post;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.PriceNLimitItem;
import app.marketandroid.SharedPreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout;
    private int chlidLayout;
    private ArrayList<myGroup> DataList;
    private LayoutInflater myinf;
    TextView groupName;
    MyAPI mMyAPI;
    int id;
    int product;

    public ExpandAdapter(Context context, int groupLay, int chlidLay, ArrayList<myGroup> DataList) {
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
//        groupAdd = (Button) convertView.findViewById(R.id.groupAdd);
        groupName = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(DataList.get(groupPosition).groupName);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }

        initMyAPI();

        final Context aContext = parent.getContext();

        TextView child_num = (TextView) convertView.findViewById(R.id.child_number);
        TextView child1 = (TextView) convertView.findViewById(R.id.child1);
        TextView child2 = (TextView) convertView.findViewById(R.id.child2);
        TextView child3 = (TextView) convertView.findViewById(R.id.child3);
        TextView child4 = (TextView) convertView.findViewById(R.id.child4);
        TextView child5 = (TextView) convertView.findViewById(R.id.child5);
        Button childBtn = (Button) convertView.findViewById(R.id.child_btn);
        if (childPosition == 0) {
            childBtn.setBackgroundColor(Color.parseColor("#00ffff00"));
            childBtn.setTextColor(Color.parseColor("#0336FF"));
            childBtn.setText("중량\n추가");
            child_num.setPadding(0, 0, 0, 0);
            child1.setPadding(0, 0, 0, 0);
            child2.setPadding(0, 0, 0, 0);
            child3.setPadding(0, 0, 0, 0);
            childBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(aContext);
                    View v = LayoutInflater.from(aContext).inflate(R.layout.add_weight_dialog,null,false);
                    builder.setView(v);
                    final AlertDialog alertDialog = builder.create();
                    final TextView title = v.findViewById(R.id.add_weight_title);
                    final EditText weight = v.findViewById(R.id.add_weight_weight_edit);
                    final EditText price = v.findViewById(R.id.add_weight_price_edit);
                    final EditText limit = v.findViewById(R.id.add_weight_limit_edit);
                    final Button ok = v.findViewById(R.id.add_weight_add_btn);
                    final Button cancel = v.findViewById(R.id.add_weight_cancel_btn);

                    title.setText(DataList.get(groupPosition).groupName + " 중량 추가");


                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            product = Integer.parseInt(DataList.get(groupPosition).child_5.get(childPosition));
                            Call<List<DemandItem>> get_demands = mMyAPI.get_demands(SharedPreferenceManager.getString(aContext,"token"));
                            get_demands.enqueue(new Callback<List<DemandItem>>() {
                                @Override
                                public void onResponse(Call<List<DemandItem>> call, Response<List<DemandItem>> response) {
                                    List<DemandItem> mList = response.body();
                                    assert mList != null;
                                    for (DemandItem item : mList){
                                        if (item.getProduct() == product){
                                            if (item.getWeight().equals(weight.getText().toString())){
                                                Toast.makeText(aContext, "이미 존재하는 중량정보 입니다.", Toast.LENGTH_SHORT).show();
                                                break;
                                            }else if (weight.getText().toString().equals("")){
                                                Toast.makeText(aContext, "중량정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                                break;
                                            }else if (price.getText().toString().equals("")){
                                                Toast.makeText(aContext, "박스 당 가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                                break;
                                            }else{
                                                DemandItem_post demandItem = new DemandItem_post();
                                                Call<DemandItem_post> post_demands = mMyAPI.post_demands(SharedPreferenceManager.getString(aContext,"token"),demandItem);
                                                demandItem.setWeight(Integer.parseInt(weight.getText().toString()));
                                                demandItem.setPrice(Integer.parseInt(price.getText().toString()));
                                                demandItem.setLimit(Integer.parseInt(limit.getText().toString()));
                                                demandItem.setProduct(product);
                                                post_demands.enqueue(new Callback<DemandItem_post>() {
                                                    @Override
                                                    public void onResponse(Call<DemandItem_post> call, Response<DemandItem_post> response) {
                                                        Toast.makeText(aContext, "중량을 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                                        Log.d("retrofit",response.message());
                                                        alertDialog.dismiss();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<DemandItem_post> call, Throwable t) {
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<DemandItem>> call, Throwable t) {
                                }
                            });
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    if (aContext != null) {
                        alertDialog.show();
                    } else {
                        Toast.makeText(aContext, "null", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            childBtn.setBackgroundResource(R.drawable.button_blue);
            childBtn.setTextColor(Color.parseColor("#ffffff"));
            childBtn.setText("수정");
            child_num.setPadding(20, 0, 0, 0);
            child1.setPadding(80, 0, 0, 0);
            child2.setPadding(60, 0, 0, 0);
            child3.setPadding(100, 0, 0, 0);

            childBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_dialog, null, false);
                    builder.setView(v);
                    final AlertDialog alertDialog = builder.create();
                    Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                    final TextView child_dial_title = v.findViewById(R.id.child_dial_title);
                    final TextView et_weight = v.findViewById(R.id.child_dial_weight_edit);
                    final EditText et_price = v.findViewById(R.id.child_dial_price_edit);
                    final EditText et_limit = v.findViewById(R.id.child_dial_limit_edit);
                    final Button btn_ok = v.findViewById(R.id.child_dial_add_btn);
                    final Button btn_no = v.findViewById(R.id.child_dial_cancel_btn);

                    et_weight.setText(DataList.get(groupPosition).child_1.get(childPosition));
                    et_price.setText(DataList.get(groupPosition).child_2.get(childPosition));
                    et_limit.setText(DataList.get(groupPosition).child_3.get(childPosition));
                    id = Integer.parseInt(DataList.get(groupPosition).child_4.get(childPosition));


                    child_dial_title.setText(DataList.get(groupPosition).groupName + " 설정 변경");



                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (et_price.getText().toString().equals("")) {
                                Toast.makeText(context, "가격은 필수 입력사항 입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                final PriceNLimitItem item = new PriceNLimitItem();
                                Call<PriceNLimitItem> post_priceNlimits = mMyAPI.post_priceNlimits(SharedPreferenceManager.getString(context, "token"), item);
                                item.setPrice(Integer.parseInt(et_price.getText().toString()));
                                item.setLimit(Integer.parseInt(et_limit.getText().toString()));
                                item.setDemand(id);
                                post_priceNlimits.enqueue(new Callback<PriceNLimitItem>() {
                                    @Override
                                    public void onResponse(Call<PriceNLimitItem> call, Response<PriceNLimitItem> response) {
                                        Toast.makeText(context, "수정 완료", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<PriceNLimitItem> call, Throwable t) {

                                    }
                                });
                                alertDialog.dismiss();
                            }
                        }
                    });
                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    if (parent.getContext() != null) {
                        alertDialog.show();
                    } else {
                        Toast.makeText(parent.getContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        child_num.setText(DataList.get(groupPosition).child__num.get(childPosition));
        child1.setText(DataList.get(groupPosition).child_1.get(childPosition));
        child2.setText(DataList.get(groupPosition).child_2.get(childPosition));
        child3.setText(DataList.get(groupPosition).child_3.get(childPosition));
        child4.setText(DataList.get(groupPosition).child_4.get(childPosition));
        child5.setText(DataList.get(groupPosition).child_5.get(childPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DataList.get(groupPosition).child__num.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DataList.get(groupPosition).child__num.size();
    }

    @Override
    public myGroup getGroup(int groupPosition) {
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    private void initMyAPI() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}

