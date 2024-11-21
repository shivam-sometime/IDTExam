package com.application.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.R
import com.application.databinding.ActivityFullScreenDetailBinding
import com.application.viewmodel.StateListViewModel
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class FullScreenDetailActivity : AppCompatActivity() {



    lateinit var binding : ActivityFullScreenDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getIntentData()

    }

    private fun getIntentData(){

        if(intent != null && intent.extras != null){

            if(intent?.extras?.containsKey("selectedStateData") == true){
                val selectedStateData = intent?.extras?.getString("selectedStateData")
                val data = JsonParser().parse(selectedStateData).asJsonArray
                if(data?.size() != 0){
                    val details =  data?.get(0)?.asJsonObject?: JsonObject()
                    if(details.has("name")){
                        binding.tvStateName.setText(details.get("name").asString)
                    }
                    if(details.has("capital")){
                        binding.tvStateCapital.setText(details.get("capital").asString)

                    }
                    if(details.has("population")){
                        binding.tvStatePopulation.setText(details.get("population").asString)

                    }
                    if(details.has("area_sq_miles")){
                        binding.tvStateArea.setText(details.get("area_sq_miles").asString)

                    }
                    if(details.has("region")){
                        binding.tvStateRegion.setText(details.get("region").asString)

                    }
                }
            }
        }

    }
}