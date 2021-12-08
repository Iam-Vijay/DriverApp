package `in`.spiegel.driverapp

import `in`.spiegel.driverapp.Model.HistoryModel
import `in`.spiegel.driverapp.UI.HomeActivity
import `in`.spiegel.driverapp.Utils.PdfGenerator
import `in`.spiegel.driverapp.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity() {
  lateinit var pdfGenerator: PdfGenerator
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pdfGenerator = PdfGenerator()
        binding.register.setOnClickListener {
            if (validateEdittext(binding.email,"Enter Your Email Address").and(validateEdittext(binding.name,"Enter Your Name"))
                    .and(validateEdittext(binding.mobile,"Enter Your Mobile No")).and(validateEdittext(binding.txno,"Enter Taxi No"))
                        .and(validateEdittext(binding.txmodel,"Enter Taxi Model"))){
                     startActivity(Intent(this,HomeActivity::class.java))
            }
        }
        var m1 = HistoryModel("Madurai","Coimbatore","205Km","1600")
        pdfGenerator.createPDF(this,"myreport","08/12/2021",m1)
    }
}