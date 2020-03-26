package chapter.android.aweme.ss.com.homework.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import chapter.android.aweme.ss.com.homework.R;
import chapter.android.aweme.ss.com.homework.model.Message;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static String TAG = "MyAdapter";
    private List<Message> mDataSet;
    private final ListItemClickListener listItemClickListener;

    public MyAdapter(List<Message> dataSet, ListItemClickListener listener) {
        mDataSet = dataSet;
        listItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ex3, parent, false);

        MyViewHolder myVH = new MyViewHolder(view);
        return myVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CircleImageView avatar;
        private final ImageView robotNotice;
        private final TextView title;
        private final TextView description;
        private final TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avatar);
            robotNotice = itemView.findViewById(R.id.robot_notice);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }

        public void bind(Message message) {
            String iconType = message.getIcon();
            int iconID = R.drawable.icon_girl;
            switch (iconType) {
                case "TYPE_ROBOT":
                    iconID = R.drawable.session_robot;
                    break;
                case "TYPE_GAME":
                    iconID = R.drawable.icon_micro_game_comment;
                    break;
                case "TYPE_SYSTEM":
                    iconID = R.drawable.session_system_notice;
                    break;
                case "TYPE_STRANGER":
                    iconID = R.drawable.session_stranger;
                    break;
                case "TYPE_USER":
                    iconID = R.drawable.icon_girl;
                    break;
                default:
                    Log.d(TAG, "Unknown iconType: " + iconType);
                    break;
            }
            avatar.setImageResource(iconID);
            robotNotice.setVisibility(message.isOfficial() ? View.VISIBLE : View.INVISIBLE);
            title.setText(message.getTitle());
            description.setText(message.getDescription());
            time.setText(message.getTime());
        }

        @Override
        public void onClick(View v) {
            int clickPos = getAdapterPosition();
            if (listItemClickListener != null) {
                listItemClickListener.onListItemClick(clickPos);
            }
        }
    }
}
