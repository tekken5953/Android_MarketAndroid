package app.marketandroid.Manager;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import app.marketandroid.R;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout;
    private int chlidLayout;
    private ArrayList<myGroup> DataList;
    private LayoutInflater myinf;
    TextView groupName;

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
        groupName = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(DataList.get(groupPosition).groupName);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        TextView child_num = (TextView) convertView.findViewById(R.id.child_number);
        TextView child1 = (TextView) convertView.findViewById(R.id.child1);
        TextView child2 = (TextView) convertView.findViewById(R.id.child2);
        TextView child3 = (TextView) convertView.findViewById(R.id.child3);
        Button childBtn = (Button) convertView.findViewById(R.id.child_btn);
        if (childPosition == 0) {
            childBtn.setBackgroundColor(Color.parseColor("#00ffff00"));
            childBtn.setTextColor(Color.parseColor("#00ffff00"));
            child_num.setPadding(0, 0, 0, 0);
            child1.setPadding(0, 0, 0, 0);
            child2.setPadding(0, 0, 0, 0);
            child3.setPadding(0, 0, 0, 0);
        } else {
            childBtn.setBackgroundResource(R.drawable.button_blue);
            childBtn.setTextColor(Color.parseColor("#ffffff"));
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
                    final EditText et_weight = v.findViewById(R.id.child_dial_weight_edit);
                    final EditText et_price = v.findViewById(R.id.child_dial_price_edit);
                    final EditText et_limit = v.findViewById(R.id.child_dial_limit_edit);
                    final Button btn_ok = v.findViewById(R.id.child_dial_add_btn);
                    final Button btn_no = v.findViewById(R.id.child_dial_cancel_btn);

                    et_weight.setText(DataList.get(groupPosition).child_1.get(childPosition));
                    et_price.setText(DataList.get(groupPosition).child_2.get(childPosition));
                    et_limit.setText(DataList.get(groupPosition).child_3.get(childPosition));

                    child_dial_title.setText(DataList.get(groupPosition).groupName + " 설정 변경");

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "수정완료", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
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

}

