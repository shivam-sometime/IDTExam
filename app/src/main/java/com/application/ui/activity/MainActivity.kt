package com.application.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.application.R
import com.application.data.model.StateList
import com.application.databinding.ActivityMainBinding
import com.application.ui.StateDetailsFragment
import com.application.ui.adapter.StateRecyclerViewAdapter
import com.application.utils.states.GenericDataState
import com.application.viewmodel.StateListViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val statesViewModel: StateListViewModel by viewModels()

    private lateinit var stateListAdapterFirst : StateRecyclerViewAdapter
    private lateinit var stateListAdapterSecond : StateRecyclerViewAdapter
    private var stateList = arrayListOf<StateList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("TAG", "onCreate: open", )

        initAdapter()
        getStateListFromServer()
        observerStateList()
        openStateDetailsFragment()
        filterStateByName()
        setClick()
    }


    private fun initAdapter(){

        stateListAdapterFirst = StateRecyclerViewAdapter(stateList) {
            // fetch the details of selected state
            statesViewModel.getStateDetailsByName(it.name)
        }
        with(binding.rvFirst) {
            this?.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            this?.adapter = stateListAdapterFirst
            Log.e("TAG", "1st adapter : set", )

        }

        stateListAdapterSecond = StateRecyclerViewAdapter(stateList){
            // fetch the details of selected state
            statesViewModel.getStateDetailsByName(it.name)
        }
        with(binding.rvSecond) {
            this?.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            this?.adapter = stateListAdapterSecond

            Log.e("TAG", "2nd adapter : set", )

        }
    }

    private fun getStateListFromServer(){
        Log.e("TAG", "Calling state list api", )
        statesViewModel.getStateList()
    }

    private fun observerStateList(){
        statesViewModel.stateList.observe(this) {
            when(it){
                is GenericDataState.Error -> {
                    binding.progress?.isVisible = false
                    Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                }
                GenericDataState.Loading -> {
                    binding.progress?.isVisible = true
                }
                is GenericDataState.Result -> {
                    binding.progress?.isVisible = false
                    stateList = it.data as ArrayList<StateList>
                    stateListAdapterFirst.updateList(stateList)
                    stateListAdapterSecond.updateList(stateList)

                }
            }
        }

        statesViewModel.stateDetail.observe(this){
            when(it){
                is GenericDataState.Error -> {
                    binding.progress?.isVisible = false
                    Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                }
                GenericDataState.Loading -> {
                    binding.progress?.isVisible = true
                }
                is GenericDataState.Result -> {
                    binding.progress?.isVisible = false
                    statesViewModel.displaySelectedStateDetails(it.data)
                }
            }
        }
    }

    private fun openStateDetailsFragment() {
        val fragment = StateDetailsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun filterStateByName(){
        binding.editSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                stateListAdapterSecond.filter.filter(s)
            }
        })
    }

    private fun setClick(){

        binding.textOpenInSecondScreen?.setOnClickListener {
            startActivity(Intent(this,FullScreenDetailActivity::class.java).also {
                it.putExtra("selectedStateData", Gson().toJson(statesViewModel.getSelectedStateValue()))
            })
        }

    }

    override fun onBackPressed() {
        val fragment =supportFragmentManager.findFragmentById(R.id.frame_container)
        if(fragment is StateDetailsFragment){
            finishAffinity()
        }else {
            super.onBackPressed()
        }

    }
}


