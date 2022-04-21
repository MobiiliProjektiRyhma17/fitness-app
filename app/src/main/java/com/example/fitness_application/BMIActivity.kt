package com.example.fitness_application

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitness_application.databinding.ActivityBmiBinding
import androidx.appcompat.app.ActionBar

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Painoindeksilaskuri"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Odota")
        progressDialog.setMessage("Laskee..")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.calculatebtn.setOnClickListener {
            val h :Float = binding.heightEdit.text.toString().toFloat()/100
            val w :Float = binding.weightEdit.text.toString().toFloat()
            val a :Float = w/(h*h)



            if (a.compareTo(15f) <= 0) {
                binding.bmiType.text = "Olet vakavasti alipainoinen!"
                binding.bmiDesc.text = "Ala syömään!"
            }else if (a.compareTo(15f) > 0 && a.compareTo(16f) <=0){
                binding.bmiType.text = "Olet alipainoinen!"
                binding.bmiDesc.text = "Huolehdi itsestäsi, syö enemmän!"
            }else if (a.compareTo(16f) > 0 && a.compareTo(18.5f) <=0){
                binding.bmiType.text = "Olet lievästi alipainoinen!"
                binding.bmiDesc.text = "Huolehdi itsestäsi, syö enemmän!"
            }else if (a.compareTo(18.5f) > 0 && a.compareTo(25f) <=0){
                binding.bmiType.text = "Olet normaalipainoinen!"
                binding.bmiDesc.text = "Onneksi olkoon! Olet hyvässä kunnossa!"
            }else if (a.compareTo(25f) > 0 && a.compareTo(30f) <=0){
                binding.bmiType.text = "Olet lievästi ylipainoinen!"
                binding.bmiDesc.text = "Olisiko otollinen aika kuntoilulle?"
            }else if (a.compareTo(30f) > 0 && a.compareTo(35f) <=0){
                binding.bmiType.text = "Olet ylipainoinen!"
                binding.bmiDesc.text = "Nyt olisi otollinen aika kuntoilulle!"
            }else if (a.compareTo(35f) > 0 && a.compareTo(45f) <=0){
                binding.bmiType.text = "Olet pahasti ylipainoinen!"
                binding.bmiDesc.text = "Tämä on vaarallista sinulle! Toimi vielä kun voit!"
            }else {
                binding.bmiType.text = "Olet uskomattoman ylipainoinen"
                binding.bmiDesc.text = "Jos totta puhutaan olen jopa vaikuttunut"
            }


            binding.result.text = a.toString()
        }


    }
}