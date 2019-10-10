package com.example.graphqlapiimplementation

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.graphquery.CreateVehicleMutation
import com.example.graphquery.GetVechileDataQuery
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



//http://localhost:8080/graphql/schema.json
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnInsert.setOnClickListener(View.OnClickListener {

           progressBar.visibility=View.VISIBLE


            val createVehicleMutation = CreateVehicleMutation.builder()
                .brandName(etBrandName.text.toString())
                .modelCode(etModelNumber.text.toString())
                .launchDate(etDate.text.toString())
                .type(etVehicleType.text.toString())
                .build()
            MyApolloClient.getMyApolloClient()
                .mutate(createVehicleMutation)
                .enqueue(object: ApolloCall.Callback<CreateVehicleMutation.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.i("Response", e.toString());
                        runOnUiThread(Runnable {
                            Toast.makeText(applicationContext,"Failure Try Again!",Toast.LENGTH_SHORT).show()

                            progressBar.visibility=View.GONE


                        })


                    }

                    override fun onResponse(response: Response<CreateVehicleMutation.Data>) {

                        Log.i("Response", response.data().toString());
                        runOnUiThread(Runnable {

                            Toast.makeText(applicationContext,"Success",Toast.LENGTH_SHORT).show()
                            progressBar.visibility=View.GONE


                        })

                    }


                }


                )



        })



        etDate.setOnClickListener(View.OnClickListener {


            initDatePicker()

        })

        btnShow.setOnClickListener(View.OnClickListener {

            progressBar.visibility=View.VISIBLE



            MyApolloClient.getMyApolloClient().query(GetVechileDataQuery.builder().build())
                .enqueue( object: ApolloCall.Callback<GetVechileDataQuery.Data>(){
                    override fun onFailure(e: ApolloException) {
                        Log.i("Response", e.toString());

                        runOnUiThread(Runnable {

                            Toast.makeText(applicationContext,"Failure Try Again",Toast.LENGTH_SHORT).show()
                            progressBar.visibility=View.GONE


                        })

                    }

                    override fun onResponse(response: Response<GetVechileDataQuery.Data>) {

                        Log.i("Response", response.data().toString());

                        runOnUiThread(Runnable {

                            Toast.makeText(applicationContext,"Success ,data available now "+response?.data()?.vehicles()?.size,Toast.LENGTH_SHORT).show()
                            progressBar.visibility=View.GONE


                        })


                    }


                }


                );

        })

    }

    private fun initDatePicker() {


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            etDate.setText("" + year + "-" + month + "-" + dayOfMonth)
        }, year, month, day)

        dpd.show()

    }
}
