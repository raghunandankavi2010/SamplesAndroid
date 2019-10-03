package com.raghu.contacts.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raghu.contacts.R
import com.raghu.contacts.ui.contact.ContactsFragment
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = "Contacts List"
        setSupportActionBar(toolbar)
        showContactsFragmentWithPermissionCheck()
    }

    @NeedsPermission(Manifest.permission.READ_CONTACTS)
    fun showContactsFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactsFragment())
                .addToBackStack("contacts fragment")
                .commitAllowingStateLoss()
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS)
    fun showRationaleForContacts(request: PermissionRequest) {
        Toast.makeText(this, R.string.permission_read_contacts_rationaled, Toast.LENGTH_SHORT).show()

    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    fun onReadContactsDenied() {
        Toast.makeText(this, R.string.permission_read_denied, Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onReadContactsNeverAskAgain() {
        Toast.makeText(this, R.string.permission_read_contacts_never_askagain, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
