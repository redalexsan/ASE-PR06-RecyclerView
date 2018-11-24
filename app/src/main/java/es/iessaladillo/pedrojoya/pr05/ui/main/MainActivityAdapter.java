package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityAdapter extends ListAdapter<User,MainActivityAdapter.ViewHolder> {

    private final OnDeleteClickListener onDeleteClickListener;
    private final OnEditClickListener onEditClickListener;

    protected MainActivityAdapter(OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return TextUtils.equals(oldItem.getName(),newItem.getName()) && TextUtils.equals(oldItem.getMail(),newItem.getMail()) && oldItem.getPhoneNumer() == newItem.getPhoneNumer() &&
                        Objects.equals(oldItem.getAvatar(),newItem.getAvatar());
            }
        });

        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item,parent,false),onDeleteClickListener, onEditClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    protected User getItem(int position) {
        return super.getItem(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName;
        private final TextView lblEmail;
        private final TextView lblPhoneNumber;
        private final TextView lblEdit;
        private final TextView lblDelete;
        private final ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView,OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
            super(itemView);

            imgAvatar = ViewCompat.requireViewById(itemView,R.id.imgAvatarUser);
            lblName = ViewCompat.requireViewById(itemView,R.id.lblName);
            lblEmail = ViewCompat.requireViewById(itemView, R.id.lblMail);
            lblPhoneNumber = ViewCompat.requireViewById(itemView,R.id.lblTelf);
            lblEdit = ViewCompat.requireViewById(itemView,R.id.lblEdit);
            lblDelete = ViewCompat.requireViewById(itemView,R.id.lblDelete);

            lblEdit.setOnClickListener(v -> onEditClickListener.onItemClick(getAdapterPosition()));
            lblDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(getAdapterPosition()));

        }

        public void bind(User profile) {
            lblName.setText(profile.getName());
            lblEmail.setText(profile.getMail());
            imgAvatar.setImageResource(profile.getAvatar().getImageResId());
            lblPhoneNumber.setText(String.valueOf(profile.getPhoneNumer()));
        }
    }
}
