package com.example.guillermo.lab42_claseasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnmostrar;
    EditText numero;
    TextView textViewmostrar;
    String cad = " 0 " ;
    int numAnt = 0 ;
    int numAct = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnmostrar=findViewById(R.id.btnserie);
        numero=findViewById(R.id.editTextNumero);
        textViewmostrar=findViewById(R.id.txtmostrar);



        btnmostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numAct=1;
                numAnt=0;
                cad="0";
                textViewmostrar.setText(cad);
                AsyncTarea asyncTarea = new AsyncTarea();
                asyncTarea.execute();
            }
        });

    }

    private void UnSegundo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class  AsyncTarea extends AsyncTask<Void, Integer,Boolean> {

        int mostrar=Integer.parseInt(numero.getText().toString());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i=1; i<mostrar; i++){
                UnSegundo();
                if(i==1){
                    cad+="\n1";
                }else {
                    int index = numAct;
                    numAct = numAnt + numAct;
                    numAnt = index;
                    publishProgress(numAct);
                }

                if (isCancelled()){
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            //Actualizar la barra de progreso
            cad+="\n"+values[0].intValue();
            textViewmostrar.setText(cad);

        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            //super.onPostExecute(aVoid);

            if (aVoid){
                Toast.makeText(getApplicationContext(),"Tarea completada",Toast.LENGTH_SHORT).show();

            }
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getApplicationContext(),"Tarea NO finaliza AsyncTask",Toast.LENGTH_SHORT).show();

        }
    }
}
