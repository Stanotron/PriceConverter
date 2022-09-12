package com.example.priceconverter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        overridePendingTransition(R.drawable.fade_in, R.drawable.fade_out)
        val view: View = findViewById(android.R.id.content)
        val mLoadAnimation: Animation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
        mLoadAnimation.duration = 1000
        view.startAnimation(mLoadAnimation)
        setupSpinner()
        textChanged()
    }
    private fun getApiResult(){
        if(etAmount!=null && etAmount.text.isNotEmpty() && etAmount.text.isNotBlank()){
            val API = "https://api.exchangerate.host/latest?base=$baseCurrency"
            Log.d("ApI","$API")

            if(baseCurrency==convertedToCurrency){
                Toast.makeText(
                    applicationContext,
                    "Cannot convert the same currency",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                  GlobalScope.launch(Dispatchers.IO){
                      try{
                          val apiResult = URL(API).readText()
                          val jsonObject = JSONObject(apiResult)

                          conversionRate = jsonObject.getJSONObject("rates").getString(convertedToCurrency).toFloat()
                          Log.d("Main","$conversionRate")
                          Log.d("Main",apiResult)
                          withContext(Dispatchers.Main){
                              val text = ((etAmount.text.toString().toFloat()) * conversionRate).toString()
                              tvOut?.setText(text)
                          }
                      }
                      catch (e:Exception){
                          Log.e("Main","$e")
                      }
                  }
            }
        }
    }

    private fun setupSpinner(){
        val s1 : Spinner = findViewById(R.id.firstspinner)
        val s2 : Spinner = findViewById(R.id.secondspinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            R.layout.spinner_text_style
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            s1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            R.layout.spinner_text_style
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            s2.adapter = adapter
        }

        s1.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })

        s2.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun textChanged(){
        btnConvert.setOnClickListener{
            try{
                getApiResult()
            }catch (e:Exception){
                Log.e("Main","&e")
            }
        }
    }
}
