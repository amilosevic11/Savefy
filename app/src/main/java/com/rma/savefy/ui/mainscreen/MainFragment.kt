package com.rma.savefy.ui.mainscreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import coil.load
import com.rma.savefy.R
import com.rma.savefy.SavefyApp
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentMainBinding
import com.rma.savefy.helpers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(), PopupMenu.OnMenuItemClickListener {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun setupUi() {
        downloadAvatar()
        observeData()
        setOnClickListeners()
    }

    private fun downloadAvatar() {
        shouldShowProgressDialog(shouldShowProgress = true)
        mainFragmentViewModel.downloadAvatar()
    }

    private fun observeData() {
        mainFragmentViewModel.isUserSignedOut.observe(viewLifecycleOwner) {
            navigateToAuthentication()
        }
        mainFragmentViewModel.userAvatar.observe(viewLifecycleOwner) {
            if(!it.equals(Uri.EMPTY)) {
                binding.sivAvatar.load(it) {
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
            shouldShowProgressDialog(shouldShowProgress = false)
        }
    }

    private fun setOnClickListeners() {
        binding.sivMenu.setOnClickListener {
            initPopupMenu(requireContext(), it, R.menu.actions, this)
        }

        binding.sivAvatar.setOnClickListener {
            checkForPermissions()
        }

        binding.mbtnAddExpense.setOnClickListener {

        }

        binding.mbtnAddRevenue.setOnClickListener {

        }
    }

    private fun checkForPermissions() {
        if(ContextCompat.checkSelfPermission(SavefyApp.application, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }
        if (ContextCompat.checkSelfPermission(SavefyApp.application, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
        else {
            initPopupMenu(requireContext(), binding.sivAvatar, R.menu.camera_or_gallery, this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeToast("Storage permission granted!", lengthLong = false)
            }
        }
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeToast("Camera permission granted!", lengthLong = false)
                initPopupMenu(requireContext(), binding.sivAvatar, R.menu.camera_or_gallery, this)
            }
        }
//        initPopupMenu(requireContext(), binding.sivAvatar, R.menu.camera_or_gallery, this)
    }

    private fun navigateToAuthentication() {
        findNavController().navigate(
            MainFragmentDirections.actionRevenuesAndExpensesFragmentToAuthenticationFragment()
        )
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.sign_out -> mainFragmentViewModel.signOut()
            R.id.camera -> startCamera()
            else -> startGallery()
        }
        return true
    }

    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)
    }

    private fun startGallery() {
        val pickIntent = Intent()
        pickIntent.type = "image/*"
        pickIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), GALLERY_CODE)
    }

    private fun shouldShowProgressDialog(shouldShowProgress: Boolean) {
        if(shouldShowProgress) {
            binding.progressDialog.progressBarBg.visibility = View.VISIBLE
        }
        else {
            binding.progressDialog.progressBarBg.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                val takenImage = data.data
                if (takenImage != null) {
                    mainFragmentViewModel.uploadAvatar(takenImage)
                    binding.sivAvatar.load(takenImage)
                }
            }
        }
        else if(requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val takenImage = data.data
                if (takenImage != null) {
                    mainFragmentViewModel.uploadAvatar(takenImage)
                    binding.sivAvatar.load(takenImage)
                }
            }
        }
    }
}