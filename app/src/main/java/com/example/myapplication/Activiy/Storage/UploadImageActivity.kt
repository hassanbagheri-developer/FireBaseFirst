package com.example.myapplication.Activiy.Storage

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_image.*
import org.jetbrains.anko.toast
import java.io.IOException

class UploadImageActivity : AppCompatActivity() {

    companion object {
        val STORAGE_PATH_UPLAOD = "Uploads/"
        val DATABSE_PATH_UPLAOD = "Upload"
        val IMAGE_REQUEST = 234
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var choseimage: Boolean = false
    private lateinit var photoString: String
    lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)


        databaseReference = FirebaseDatabase.getInstance().getReference(DATABSE_PATH_UPLAOD)
        storageRef = FirebaseStorage.getInstance().getReference(STORAGE_PATH_UPLAOD)

        edt_imgTitle.visibility = View.INVISIBLE
        txt_shownameimage.visibility = View.INVISIBLE

        btn_choose.setOnClickListener {
            if (choseimage) {

                uploadfile()
                choseimage = false

            } else {
                showfileandchose()
                choseimage = true
            }
        }

//        fab_addimage.setOnClickListener {
//
//            startActivity<UploadImageActivity>()
//        }
    }

    private fun uploadfile() {

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("در حال بارگزاری تصویر")
            progressDialog.show();


            val sRef = storageRef.child(
                STORAGE_PATH_UPLAOD + System.currentTimeMillis() + "." + getFileExtension(filePath)
            );

            val uploadTask = sRef.putFile(filePath)
//            sRef.putFile(filePath)
            uploadTask.addOnSuccessListener {

                progressDialog.dismiss();
                toast("فایل مورد نظر با موفقیت آپلود شد");

                val result = it.metadata?.getReference()?.getDownloadUrl();
                result?.addOnSuccessListener {

                    if (!it.equals("")) {
                        val photoStringLink = it.toString();


                        val upload = Upload(edt_imgTitle.getText().toString(), photoStringLink);
                        val uploadID = databaseReference.push().getKey()
                        databaseReference.child(uploadID.toString()).setValue(upload);

                        img_bigimage.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud_upload_black_24dp));
                        edt_imgTitle.setVisibility(View.INVISIBLE);
                        txt_shownameimage.setVisibility(View.INVISIBLE);

                        txt_showhelp.setVisibility(View.VISIBLE);
                        img_help.setVisibility(View.VISIBLE);
                        btn_choose.setText("انتخاب تصویر");


                    } else {
                        toast("خطا در آپلود !");
                    }
                }
            }.addOnFailureListener {

                toast(it.message.toString())
            }.addOnProgressListener {
                val progress: Double =
                    100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                progressDialog.setMessage("در حال بارگزاری ..." + progress.toInt() + "%..")
            }


        }
    }

    fun getFileExtension(uri: Uri?): String? {
        val resolver = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(resolver.getType(uri!!))
    }

    fun showfileandchose() {

        choseimage = true
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "لطفا عکس خود را انتخاب کنید!"),
            IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            Log.e("hassan", data.toString())
            Log.e("hassan", "============")
            Log.e("hassan", data.data.toString())
            filePath = data.data!!
            try {

                edt_imgTitle.visibility = View.VISIBLE
                txt_shownameimage.visibility = View.VISIBLE
                txt_showhelp.visibility = View.INVISIBLE
                img_help.visibility = View.INVISIBLE
                val bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                img_bigimage.setImageBitmap(bitmap)
                btn_choose.setText("آپلود عکس")
            } catch (e: IOException) {
                e.printStackTrace()
                toast(e.message.toString())
            }
        }

    }

}



