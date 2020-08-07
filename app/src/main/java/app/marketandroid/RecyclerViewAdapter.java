package app.marketandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<RecyclerItem> mData;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapter(ArrayList<RecyclerItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);

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
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        RecyclerItem item = mData.get(position);

        holder.icon.setImageDrawable(item.getIconDrawable());
        holder.title.setText(item.getNameStr());
        holder.detail.setText(item.getDetailStr());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView detail;
        ImageView pop;

        ViewHolder(View itemView) {
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
            icon = itemView.findViewById(R.id.recycle_img);
            title = itemView.findViewById(R.id.recycle_name);
            detail = itemView.findViewById(R.id.recycle_detail);
            pop = itemView.findViewById(R.id.recycle_pop);

            pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Context context = view.getContext();
                    PopupMenu popup = new PopupMenu(context, pop);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.pop_edit:
                                    Toast.makeText(context, "수정하기", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.pop_delete:
                                    Toast.makeText(context, "삭제하기", Toast.LENGTH_SHORT).show();
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
