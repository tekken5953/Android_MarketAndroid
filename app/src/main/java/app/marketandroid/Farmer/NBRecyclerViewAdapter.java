package app.marketandroid.Farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.marketandroid.R;

public class NBRecyclerViewAdapter extends RecyclerView.Adapter<NBRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NBRecyclerItem> mData;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    NBRecyclerViewAdapter(ArrayList<NBRecyclerItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public NBRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.nbrecyclerview, parent, false);
        NBRecyclerViewAdapter.ViewHolder vh = new NBRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(NBRecyclerViewAdapter.ViewHolder holder, int position) {

        NBRecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIconDrawable());
        holder.time.setText(item.getTimeStr());
        holder.product.setText(item.getProductsStr());
        holder.weight.setText(item.getWeightStr());
        holder.count.setText(item.getCountStr());
        holder.total_price.setText(item.getTotal_priceStr());
        holder.personal_price.setText(item.getPersonal_priceStr());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView time, product, weight, count, total_price, personal_price;
        ImageView pop;

        ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });

            // 뷰 객체에 대한 참조. (hold strong reference)
            icon = itemView.findViewById(R.id.nbrecycle_img);
            time = itemView.findViewById(R.id.nbrecycle_time);
            product = itemView.findViewById(R.id.mgrecycle_products);
            weight = itemView.findViewById(R.id.mgrecycle_weight);
            total_price = itemView.findViewById(R.id.mgrecycle_total_price);
            personal_price = itemView.findViewById(R.id.mgrecycle_personal_price);
            count = itemView.findViewById(R.id.mgrecycle_count);
            pop = itemView.findViewById(R.id.nbrecycle_pop);

            pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Context context = view.getContext();
                    final PopupMenu popup = new PopupMenu(context, pop);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.pop_edit:
                                    Toast.makeText(context, "수정완료", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.pop_delete:
                                    Toast.makeText(context, "삭제완료", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });

        }
    }
}
