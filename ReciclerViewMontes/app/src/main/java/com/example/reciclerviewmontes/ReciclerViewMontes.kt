package com.example.reciclerviewmontes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reciclerviewmontes.adapter.MontesAdapter
import com.example.reciclerviewmontes.databinding.ActivityReciclerViewMontesBinding
import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset


class ReciclerViewMontes : AppCompatActivity() {
    lateinit var btnAddMonte : Button
    private lateinit var binding: ActivityReciclerViewMontesBinding

    private lateinit var monteMutableList: MutableList<Monte>

    private lateinit var adapter: MontesAdapter

    private lateinit var copyList:MutableList<Monte>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReciclerViewMontesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        retrieveMonte()

        binding.svMonte.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList: MutableList<Monte> =
                        copyList.filter { it.nombre.lowercase().contains(newText)
                        }.toMutableList()

                    adapter.filterByName(filteredList)
                }
                return false
            }
        })
    }

    private fun initRecyclerView() {
        monteMutableList = getListFromJson().toMutableList()
        adapter = MontesAdapter(
            MontesList = monteMutableList,
            onClickListener = { monte -> onItemSelected(monte) },
            onClickDelete = { position -> onDeleteItem(position) }
        )

        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)

        binding.rvMontes.layoutManager = manager
        binding.rvMontes.adapter = adapter
        binding.rvMontes.addItemDecoration(decoration)

        binding.btnAddMonte.setOnClickListener {
            val intent= Intent(this, MonteAdd::class.java)
            startActivity(intent)
        }
    }

    private fun onDeleteItem(position: Int) {
        monteMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun onItemSelected(pizza: Monte) {
    }

    private fun retrieveMonte() {
        val bundle = intent.extras
        if (bundle != null) {
            val monteN = bundle.getString("monte")
            val urlmonte = bundle.getString("urlmonte")
            if (monteN != null && urlmonte != null) {
                val monte = Monte(
                    nombre = monteN,
                    altura = "Desconocido",
                    continente = "Desconocido",
                    foto = urlmonte
                )
                monteMutableList.add(monte)
                adapter.notifyItemInserted(monteMutableList.size - 1)
            }
        }
    }

    fun getListFromJson(): MutableList<Monte> {
        val json: String? = getJsonFromAssets(this, "montes.json")
        val montegList = Gson().fromJson(json, Array<Monte>::class.java).toMutableList()
        copyList = montegList

        return montegList
    }

    fun getJsonFromAssets(context: Context, file: String): String? {
        var json="";
        val stream: InputStream =context.assets.open(file)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        json = String(buffer, Charset.defaultCharset())
        return json
    }


}