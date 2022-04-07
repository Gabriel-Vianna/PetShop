package br.edu.infnet.petshop.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.ServicoModel
import br.edu.infnet.petshop.databinding.ActivityPerfilBinding
import br.edu.infnet.petshop.ui.listaservicos.ServicoItemAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_perfil.recyclerViewHistory

class PerfilActivity : AppCompatActivity() {

    private var servicoItemAdapter: RecyclerView.Adapter<ServicoItemAdapter.ViewHolder>? = null
    lateinit var binding: ActivityPerfilBinding
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }

        }

        val arrayList = ArrayList<ServicoModel>()

        arrayList.add(
            ServicoModel(
                "Banho", "Serviço realizado em:", "01/12/2021", "14:00h", "R$50,00",
                R.drawable.banho_logo
            )
        )
        arrayList.add(
            ServicoModel(
                "Tosa", "Serviço realizado em:", "01/12/2021", "13:00h", "R$70,00",
                R.drawable.tosa_logo
            )
        )
        arrayList.add(
            ServicoModel(
                "Tosa", "Serviço realizado em:", "01/12/2021", "13:00h", "R$70,00",
                R.drawable.tosa_logo
            )
        )
        arrayList.add(
            ServicoModel(
                "Tosa", "Serviço realizado em:", "01/12/2021", "13:00h", "R$70,00",
                R.drawable.tosa_logo
            )
        )


        recyclerViewHistory.adapter = servicoItemAdapter
        recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = PerfilServicoItemAdapter(arrayList, this.context)
        }
    }

    fun startAdSense(view: View) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

}