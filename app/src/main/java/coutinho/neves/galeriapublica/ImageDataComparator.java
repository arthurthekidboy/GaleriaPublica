package coutinho.neves.galeriapublica;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ImageDataComparator extends DiffUtil.ItemCallback<ImageData> {

    // recebe dois objetos do tipo ImageData e retorna true caso eles sejam iguais e false caso contrario
    @Override
    public boolean areItemsTheSame (@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        //O ID é unico
        return oldItem.uri.equals(newItem.uri);
    }

    //esse metodo recebe dois do tipo imageData e retorna true caso eles tenham o mesmo conteudo e false caso contrario

    @Override
    public boolean areContentsTheSame(@NonNull ImageData oldItem, ImageData newItem ) {
        return oldItem.uri.equals(newItem.uri);
    }
}
