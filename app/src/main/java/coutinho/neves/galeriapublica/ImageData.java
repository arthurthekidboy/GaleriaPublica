package coutinho.neves.galeriapublica;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class ImageData {


    //endereço uri do arquivo da foto

    public Uri uri;

    // imagem em miniatura da foto

    public Bitmap thumb;

    //nome do arquivo da foto

    public String fileName;

    //data em que a foto foi criada

    public Date date;

    //tamanho em bytes do arquivo de foto

    public int size;

    public ImageData(Uri uri, Bitmap thumb, String fileName, Date date, int size) {
        this.uri = uri;
        this.thumb = thumb;
        this.fileName = fileName;
        this.date = date;
        this.size = size;
    }

}
