package kailaine.mobile.trabalho_semestral_android_controle_financeiro;
/*
 *@author:<Kailaine Almeida de Souza RA: 1110482313026>
 */
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadFragment(bundle);
        } else {
            replaceFragment(new InicioFragment());
        }
    }
    private void loadFragment(Bundle bundle) {
        String fragmentType = bundle.getString("fragment_type");

        switch (fragmentType) {
            case "Entrada":
                fragment = new NovaEntradaFragment();
                break;
            case "Visualizar":
                fragment = new VisualizarMetasFragment();
                break;
            case "Historico":
                fragment = new HistoricoFragment();
                break;
            case "Meta":
                fragment = new NovaMetaFragment();
                break;
            case "Reserva":
                fragment = new NovaReservaFragment();
                break;
            default:
                fragment = new InicioFragment();
                break;
        }

        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (itemId == R.id.Item_inicio) {
            bundle.putString("fragment_type", "Inicio");
        } else if (itemId == R.id.Item_entrada) {
            bundle.putString("fragment_type", "Entrada");
        } else if (itemId == R.id.Item_VisualizarMetas) {
            bundle.putString("fragment_type", "Visualizar");
        } else if (itemId == R.id.Item_historico) {
            bundle.putString("fragment_type", "Historico");
        } else if (itemId == R.id.Item_meta) {
            bundle.putString("fragment_type", "Meta");
        } else if (itemId == R.id.item_reserva) {
            bundle.putString("fragment_type", "Reserva");
        } else {
            return super.onOptionsItemSelected(item);
        }

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        return true;
    }
}