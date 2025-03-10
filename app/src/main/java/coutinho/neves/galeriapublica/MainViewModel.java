package coutinho.neves.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.List;

import kotlinx.coroutines.CoroutineScope;

// MainViewModel herda de AndroidViewModel não de ViewModel
public class MainViewModel extends AndroidViewModel {
    LiveData<PagingData<ImageData>> pageLv;
    int navigationOpSelected = R.id.gridViewOp;


    //A classe AndroidViewModel é uma especialização de ViewModel que possui como parâmetro de entrada em seu construtor uma instancia da aplicação
    public MainViewModel(@NonNull Application application) {
        super(application);

        GalleryRepository galleryRepository = new GalleryRepository(application);
        GalleryPagingSource galleryPagingSource = new GalleryPagingSource(galleryRepository);
        Pager<Integer, ImageData> pager = new Pager(new PagingConfig(10), () -> galleryPagingSource);
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        pageLv = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

    }
    public LiveData<PagingData<ImageData>> getPageLv() {
        return pageLv;
    }

//mostra os metodos para pegar e setar o valor
    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;

    }
}
