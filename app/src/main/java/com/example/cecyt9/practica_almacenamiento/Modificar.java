package com.example.cecyt9.practica_almacenamiento;

/**
 * Created by CECyT9 on 06/06/2017.
 */
        import android.app.Activity;
        import android.app.TimePickerDialog;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import org.w3c.dom.Text;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

public class Modificar extends Activity implements SeekBar.OnSeekBarChangeListener {

    SeekBar personas;
    EditText nombre;
    boolean update=false;
    TextView numPersonas;
    String numPers="";
    private String ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        try {
            personas=(SeekBar)findViewById(R.id.personasReserva);
            personas.setOnSeekBarChangeListener(this);
            numPersonas=(TextView)findViewById(R.id.numPersonas);
            nombre=(EditText)findViewById(R.id.nombreReserva);
            Calendar fechaSeleccionada = Calendar.getInstance();
            fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 12); // hora inicial
            fechaSeleccionada.clear(Calendar.MINUTE); // 0
            fechaSeleccionada.clear(Calendar.SECOND); // 0

            ArrayList<String> registro = this.getIntent().getStringArrayListExtra("Registro");
            nombre.setText(registro.get(1));
            numPersonas.setText("Personas: "+registro.get(2));
            personas.setProgress(Integer.parseInt(registro.get(2)));
            Toast.makeText(getApplicationContext(),"Hora de reservación: "+registro.get(3),Toast.LENGTH_SHORT).show();

            ID=registro.get(0);
            update=true;
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void modificarReserva(View vw)
    {
        try {
            AuxiliarSQL sql = new AuxiliarSQL(this, "DB_Restaurant", null, 1);
            final SQLiteDatabase db = sql.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("Nombre", nombre.getText().toString());
            valores.put("Personas", numPers);
            db.update("Reservacion", valores, "_id=" + ID, null);
            Toast.makeText(getApplicationContext(),"Reserva modificada",Toast.LENGTH_LONG).show();
            Intent menuPrincipal=new Intent(this,MainActivity.class);
            finish();
            startActivity(menuPrincipal);
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error al modificar registro",Toast.LENGTH_LONG).show();
        }
    }
    public void eliminarReserva(View vw)
    {
        AuxiliarSQL sql = new AuxiliarSQL(this,	"DB_Restaurant", null, 1);
        final SQLiteDatabase db = sql.getWritableDatabase();
        db.delete("Reservacion","_id="+ID,null);
        Toast.makeText(getApplicationContext(),"Se ha eliminado la reservación.",Toast.LENGTH_SHORT).show();
        Intent menuPrincipal=new Intent(this,MainActivity.class);
        finish();
        startActivity(menuPrincipal);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(update)
        {

            numPers=""+(personas.getProgress()+1);
            numPersonas.setText("Personas: "+numPers);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
