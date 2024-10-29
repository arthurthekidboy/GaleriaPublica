package coutinho.neves.galeriapublica;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.text.SimpleDateFormat;

// herda uma classe especifica de paging 3, a classe PagingDataAdapter
public class ListAdapter extends PagingDataAdapter<ImageData, MyViewHolder> {
    public ListAdapter(@NonNull DiffUtil.ItemCallback<ImageData> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override

    //o onCreateViewHolder e onBindViewHolder tem o mesmo comportamento de um adapter normal

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageData imageData = getItem(position);
        TextView tvName = holder.itemView.findViewById(R.id.tvName);
        tvName.setText(imageData.fileName);
        TextView tvDate = holder.itemView.findViewById(R.id.tvDate);
        tvDate.setText(String.format("Data: " + new SimpleDateFormat("HH:mm dd/MM/YYYY").format(imageData.date)));
        TextView tvSize = holder.itemView.findViewById(R.id.tvSize);
        tvSize.setText("Tamanho: " + String.valueOf(imageData.size));

        Bitmap thumb = imageData.thumb;
        ImageView imageView = holder.itemView.findViewById(R.id.imThumb);
        imageView.setImageBitmap(thumb);
    }
}
