package com.example.ideatapp.presentation.ui.scanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.ideatapp.databinding.FragmentScannerBinding
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.presentation.ui.history.RiwayatActivity
import com.example.ideatapp.presentation.viewmodel.ScannerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private val scanViewModel: ScannerViewModel by viewModel()
    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "Tidak ada foto yang dipilih")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        observeViewModel()
    }

    private fun setClickListeners() {
        binding.galleryButton.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.cameraButton.setOnClickListener {
            currentImageUri = getImageUri(requireContext())
            launcherIntentCamera.launch(currentImageUri!!)
        }

        binding.uploadButton.setOnClickListener {
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                scanViewModel.uploadImage(imageFile)
            } ?: Toast.makeText(requireContext(), "Pilih atau ambil gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        scanViewModel.scanResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultUtil.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                is ResultUtil.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    showToast("Scan berhasil: ${result.data.message}")
                }
                is ResultUtil.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    showToast("Error: ${result.message}")
                }
            }
        }

        scanViewModel.navigateToRiwayat.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                val intent = Intent(requireContext(), RiwayatActivity::class.java)
                startActivity(intent)
                scanViewModel.setNavigateToRiwayat(false)
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
