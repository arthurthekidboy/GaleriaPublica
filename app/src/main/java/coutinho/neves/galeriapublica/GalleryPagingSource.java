package coutinho.neves.galeriapublica;

import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


//GalleryPagingSource herda de ListenableFuturePagingSource

public class GalleryPagingSource extends ListenableFuturePagingSource<Integer, ImageData> {
    GalleryRepository galleryRepository;
    Integer initialLoadSize = 0;

//recebe como parametro uma instancia de GalleryRepository que sera usada para consultar os dados e montar as paginas de dados

    public GalleryPagingSource(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Nullable
    @Override

    //retorna nulo
    public Integer getRefreshKey(@Nonnull PagingState<Integer, ImageData> pagingState) {
        return null;
    }

    @Nonnull
    @Override

    //é responsavel por carregar uma pagina do GalleryRepository e retorna-lo encapsulado em um objeto ListenableFuture

    public ListenableFuture<LoadResult<Integer, ImageData>> loadFuture(@Nonnull LoadParams<Integer> loadParams) {

        //corresponde a pagina de dados que deve ser obtida neste momento

        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
            initialLoadSize = loadParams.getLoadSize();
        }
        Integer offSet = 0;
        if (nextPageNumber == 2) {
            offSet = initialLoadSize;

        } else {
            offSet = ((nextPageNumber - 1) * loadParams.getLoadSize()) + (initialLoadSize - loadParams.getLoadSize());
        }

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        Integer finalOffset = offSet;
        Integer finalNextPageNumber = nextPageNumber;
        ListenableFuture<LoadResult<Integer, ImageData>> lf = service.submit(new Callable<LoadResult<Integer, ImageData>>() {
            @Override
            public LoadResult<Integer, ImageData> call() throws Exception {
                List<ImageData> imageDataList = null;
                try {
                    imageDataList = galleryRepository.loadImageData(loadParams.getLoadSize(), finalOffset);
                    Integer nextKey = null;
                    if (imageDataList.size() >= loadParams.getLoadSize()) {
                        nextKey = finalNextPageNumber + 1;

                    }
                    return new LoadResult.Page<Integer, ImageData>(imageDataList, null, nextKey);

                } catch (FileNotFoundException e) {
                    return new LoadResult.Error<>(e);
                }
            }
        });
        return lf;
    }
}
