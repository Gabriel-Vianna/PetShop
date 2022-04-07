package br.edu.infnet.petshop.ui.servico

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import br.edu.infnet.petshop.R
import br.edu.infnet.petshop.ServicoModel
import br.edu.infnet.petshop.databinding.ActivityServicoBinding
import br.edu.infnet.petshop.utils.ServicoEnum

class ServicoActivity : AppCompatActivity() {

    lateinit var binding: ActivityServicoBinding
    val arrayList = ArrayList<ServicoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val servicoId = ServicoEnum.getById(intent.getStringExtra("SERVICO")!!.toInt())
        servicoId?.image?.let { binding.imagemServico.setImageResource(it) }
        binding.tituloServico.text = servicoId?.title
        binding.subtituloServico.text = "${servicoId?.days}, ${servicoId?.hour}"
        binding.btnAgendar.setOnClickListener {

            val servico: ServicoModel = ServicoModel(
                binding.tituloServico.text.toString(),
                "Atendimento",
                binding.diaServico.text.toString(),
                binding.horarioServico.text.toString(),
                servicoId!!.value,
                binding.imagemServico.getTag().toString().toInt()
            )

            arrayList.add(servico)
        }
    }
}