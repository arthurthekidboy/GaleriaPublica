package coutinho.neves.galeriapublica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {

    //define o bottomNavigationView como um atribuito da MainActivity. Isso é necessario porque utilizaremos a referencia em outros metodos da classe.

    BottomNavigationView bottomNavigationView;

    static int RESULT_REQUEST_PERMISSION = 2;

    //o metodo setFragment recebe como parametro um fragment.

    void setFragment(Fragment fragment) {

        //transação do gerenciador de fragmentos

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //esse fragment sera setado no espaço definido pelo elemento de UI fragContainer

        fragmentTransaction.replace(R.id.fragContainer, fragment);

        //indica o fragmento que agora faz parte da pilha de tela do botão voltar do Android

        fragmentTransaction.addToBackStack(null);

        // realiza o commit da transação

        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);
    }


    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                permissionsNotGranted.add(permission);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsNotGranted.size() > 0) {
                        requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);


                    }

                    // caso o app já possua todas as permissões que precisa

                    else {

                        //acessa a MainViewModel e obtem a opção que foi escolhida pelo usuario

                        MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
                        int navigationOpSelected = vm.getNavigationOpSelected();

                        //seta a opção em bottonNavigationView

                        bottomNavigationView.setSelectedItemId(navigationOpSelected);
                    }
                }
            }
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {
            for(String permission : permissions) {
                if(!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }


        if(permissionsRejected.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this).setMessage("Para usar essa app é preciso conceder essas permissoes").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);


                        }
                    }).create().show();
                }
            }
        }

        //verifica se todas as permissõess foram fornecidas pelo usuario

        else{

            //caso positivo é realizado o mesmo procedimento

            MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
            int navigationOpSelected = vm.getNavigationOpSelected();
            bottomNavigationView.setSelectedItemId(navigationOpSelected);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //obtem a referencia para MainViewModel

        final MainViewModel vm = new ViewModelProvider(this).get(MainViewModel.class);
        //obtem a referencia para o BottonNavigationView

        bottomNavigationView = findViewById(R.id.btNav);

        //um "escutador" de eventos de seleção de menu.Assim toda vez que o usuario selecionar uma das opções, o metodo onNavigationItemSelected será chamado, indicando qual opção foi escolhida

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //guarda dentro de MainViewModel a opção que foi escolhida pelo usuario

                vm.setNavigationOpSelected(item.getItemId());

                //caso o usuario selecione o gridViewOp, sera criado um fragmento do tipo GridViewFragment e o mesmo sera setado em MainActivity

                    if (item.getItemId() == R.id.gridViewOp) {
                        GridViewFragment gridViewFragment = GridViewFragment.newInstance();
                        setFragment(gridViewFragment);

                    }


                    //caso o usuario selecione listViewOp, sera criado um fragmento do tipo ListViewFragment e o mesmo sera setado em MainActivity

                    if(item.getItemId() == R.id.listViewOp) {
                        ListViewFragment listViewFragment = ListViewFragment.newInstance();
                        setFragment(listViewFragment);
                }
                return true;
            }
        });
    }
}
