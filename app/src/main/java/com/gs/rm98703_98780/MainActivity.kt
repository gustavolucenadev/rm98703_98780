package com.gs.rm98703_98780

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gs.rm98703_98780.model.ItemModel
import com.gs.rm98703_98780.model.ItemsViewModelFactory
import com.gs.rm98703_98780.util.ItemsAdapter
import com.gs.rm98703_98780.viewmodel.ItemsViewModel
import java.util.Calendar
import android.content.Intent

class MainActivity : AppCompatActivity() {

    // Componentes de UI
    private lateinit var eventLocation: EditText
    private lateinit var eventType: EditText
    private lateinit var eventImpactLevel: EditText
    private lateinit var eventDate: EditText
    private lateinit var eventAffectedPeopleNumber: EditText

    // ViewModel e Adapter
    private lateinit var viewModel: ItemsViewModel
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        initializeViews()
        setupRecyclerView()
        setupDatePicker()
        setupButtonListener()
        initializeViewModel()
        setupObservers()

        val buttonOpenRms = findViewById<Button>(R.id.buttonOpenRms)
        buttonOpenRms.setOnClickListener {
            val intent = Intent(this, RmsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Registro de evento"
    }

    private fun initializeViews() {
        eventLocation = findViewById(R.id.eventLocation)
        eventType = findViewById(R.id.eventType)
        eventImpactLevel = findViewById(R.id.eventImpactLevel)
        eventDate = findViewById(R.id.eventDate)
        eventAffectedPeopleNumber = findViewById(R.id.eventAffectedPeopleNumber)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter
    }

    private fun setupDatePicker() {
        eventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val formattedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
                    eventDate.setText(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupButtonListener() {
        findViewById<Button>(R.id.button).setOnClickListener {
            if (!validateInputs()) return@setOnClickListener

            val newEvent = createEventFromInputs()
            viewModel.addItem(newEvent)
            clearInputFields()
        }
    }

    private fun validateInputs(): Boolean {
        if (isAnyFieldEmpty()) {
            eventLocation.error = "Preencha um valor"
            return false
        }

        if (eventAffectedPeopleNumber.text.toString().toInt() <= 0) {
            eventAffectedPeopleNumber.error = "O Número deve ser maior que 0"
            return false
        }

        return true
    }

    private fun createEventFromInputs(): ItemModel {
        return ItemModel(
            local = eventLocation.text.toString(),
            tipo = eventType.text.toString(),
            impacto = eventImpactLevel.text.toString(),
            data = eventDate.text.toString(),
            afetados = eventAffectedPeopleNumber.text.toString().toInt()
        )
    }

    private fun initializeViewModel() {
        val factory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(ItemsViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }

    private fun isAnyFieldEmpty(): Boolean {
        return eventLocation.text.isEmpty() ||
                eventType.text.isEmpty() ||
                eventImpactLevel.text.isEmpty() ||
                eventDate.text.isEmpty() ||
                eventAffectedPeopleNumber.text.isEmpty()
    }

    private fun clearInputFields() {
        eventLocation.text.clear()
        eventType.text.clear()
        eventImpactLevel.text.clear()
        eventDate.text.clear()
        eventAffectedPeopleNumber.text.clear()
        eventLocation.error = null
        eventAffectedPeopleNumber.error = null
    }
}