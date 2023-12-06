package com.example.flo.ui.main.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.flo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentEditbar(private val lockerFragment: LockerFragment): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_editbar, container, false)
        val delete: ImageView = view.findViewById(R.id.editbar_delete_iv)
        val selectImg: ImageView = view.findViewById(R.id.editbar_delete_iv)

        setCancelable(false)

        delete.setOnClickListener {
            lockerFragment.setSelectStatus(true)
            dismiss()
        }

        return view
    }
}