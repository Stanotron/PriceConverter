package com.example.priceconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var Currency : currency = currency.Usd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Setup()

        btnConvert.setOnClickListener{
            var Amount : Double = (etAmount.text.toString()).toDouble()
            var out : Double = Calculate(Amount)
            tvOut.text = out.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_curr,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.choose_currency -> {
                showNewCurrency()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(title : String, view: View?, positiveClickListener: View.OnClickListener){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("OK"){_,_ ->
                positiveClickListener.onClick(null)
            }.show()
    }

    private fun showNewCurrency(){
        val currencyView = LayoutInflater.from(this).inflate(R.layout.currencies,null)
        val currRadio = currencyView.findViewById<RadioGroup>(R.id.radioGroup)
        when(Currency){
            currency.Usd -> currRadio.check(R.id.rbUsdt)
            currency.Euro -> currRadio.check(R.id.rbEuro)
            currency.Pound -> currRadio.check(R.id.rbPound)
            currency.Yen -> currRadio.check(R.id.rbYen)
        }
        showAlertDialog("Choose new currency", currencyView, View.OnClickListener {
            Currency = when(currRadio.checkedRadioButtonId){
                R.id.rbUsdt -> currency.Usd
                R.id.rbPound -> currency.Pound
                R.id.rbEuro -> currency.Euro
                else -> currency.Yen
            }
            Setup()
        })
    }

    private fun Setup(){
        when(Currency){
            currency.Euro -> {
                tvAmount.text = "Enter the amount in INR ₹ :-"
                tvConvert.text = "Amount in Euro € :-"
                tvOut.text = "0.00"
                etAmount.hint = "0.00"
            }
            currency.Usd ->{
                tvAmount.text = "Enter the amount in INR ₹ :-"
                tvConvert.text = "Amount in US Dollar $ :-"
                etAmount.hint = "0.00"
                tvOut.text = "0.00"
            }
            currency.Pound -> {
                tvAmount.text = "Enter the amount in INR ₹ :-"
                tvConvert.text = "Amount in Pound £ :-"
                etAmount.hint = "0.00"
                tvOut.text = "0.00"
            }
            currency.Yen -> {
                tvAmount.text = "Enter the amount in INR ₹ :-"
                etAmount.hint = "0.00"
                tvConvert.text = "Amount in Yen ¥ :-"
                tvOut.text = "0.00"
            }
        }
    }

    private fun Calculate(amount: Double): Double {
        var back : Double = 0.0
        if(tvConvert.text == "Amount in Euro € :-") back = amount / 82.17
        else if (tvConvert.text =="Amount in US Dollar $ :-") back = amount / 79.59
        else if (tvConvert.text == "Amount in Pound £ :-") back = amount / 97.07
        else if (tvConvert.text == "Amount in Yen ¥ :-") back= amount / 0.60
        return back
    }
}