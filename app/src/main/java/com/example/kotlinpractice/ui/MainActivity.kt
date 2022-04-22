package com.example.kotlinpractice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpractice.R
import com.example.kotlinpractice.Retrofit.RetrofitClient
import com.example.kotlinpractice.adapter.itemAdapter
import com.example.kotlinpractice.databinding.ActivityMainBinding
import com.example.kotlinpractice.model.DataModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<DataModel>
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: itemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getData()

    }

    private fun getData() {
        CoroutineScope(IO).launch {
            val call :retrofit2.Call<JsonObject> = RetrofitClient.getInstance().getApi().getData()

            call.enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: retrofit2.Call<JsonObject>, response: Response<JsonObject>) {

                    val obj = response.body()
                    val listjson = obj?.get("entries")?.asJsonArray

                    list = getList(listjson)
                    setListinrec(list)

                }

                override fun onFailure(call: retrofit2.Call<JsonObject>, t: Throwable) {
                    Log.w("response1", "failed $t")
                }

            })
        }
    }

    private fun setListinrec(list: java.util.ArrayList<DataModel>) {
        itemAdapter = itemAdapter(this, list)
        binding.rec.setHasFixedSize(true)
        binding.rec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rec.adapter = itemAdapter
    }

    private fun getList(listjson: JsonArray?): ArrayList<DataModel> {
        val tmplist = arrayListOf<DataModel>()

        for (element in listjson!!){
            val obj = element.asJsonObject
            val dataModel = DataModel(
                obj.get("API").asString,
                obj.get("Description").asString,
                obj.get("Auth").asString,
                obj.get("Category").asString,
            )

            if (tmplist.size>99){
                break
            }
            tmplist.add(dataModel)
        }

        return tmplist
    }
}