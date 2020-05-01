package com.zeptolearn.epoxypoc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.zeptolearn.epoxypoc.db.entity.Containers
import com.zeptolearn.epoxypoc.ui.main.SectionsPagerAdapter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    lateinit var file1: Containers
    lateinit var file2: Containers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        downloadJsonFile()
    }

    private fun downloadJsonFile() {
        GlobalScope.launch(Dispatchers.Main) {
            val courseCompleted = async(Dispatchers.IO) {
                getFileFromSever()
            }
            showFileCompletedResult() // back on UI thread
        }
    }

    private fun showFileCompletedResult() {
        tabs.visibility = View.VISIBLE
        view_pager.visibility = View.VISIBLE

        val tabList :MutableList<String> = mutableListOf<String>()
        tabList.add("TAB1")
        tabList.add("TAB2")

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager,tabList)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }

    private fun getFileFromSever() {
        val gson = GsonBuilder().setPrettyPrinting().create()
         val strfile1Path = downloadFile("http://alrn.in/homepage.json",
            getExternalFilesDir(null).toString() + File.separator + "temp" + File.separator)
         val strFile1 = getJsonDataFromFile(strfile1Path)
        file1 = gson.fromJson(strFile1,
            Containers::class.java)



         val strfile2path = downloadFile("http://alrn.in/homepage1.json",
            getExternalFilesDir(null).toString() + File.separator + "temp" + File.separator)
        val strfile2 = getJsonDataFromFile(strfile2path)
        file2 = gson.fromJson(strfile2,
            Containers::class.java)
    }


    fun downloadFile(stUrl: String, destnUrl: String): String {
        var str = "fail"
        try {
            val url = URL(stUrl)
            val connection = url.openConnection()
            connection.connect()
            if (createDirIfNotExists(destnUrl)) {
                val fileName = stUrl.substring(stUrl.lastIndexOf("/") + 1)
                val outputFile = File(File(destnUrl), fileName)
                val fos = FileOutputStream(outputFile)
                val input = BufferedInputStream(url.openStream(), 8192)
                val buffer = ByteArray(1024)
                var len1 = 0
                while ({ len1 = input.read(buffer); len1 }() != -1) {
                    fos.write(buffer, 0, len1)
                }
                // flushing output
                fos.flush()

                fos.close()
                input.close()
                str = destnUrl + fileName
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return str
    }

    private fun createDirIfNotExists(path: String): Boolean {
        var ret = true
        val file = File(path)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false
            }
        }
        return ret
    }

    private fun getJsonDataFromFile(sPath: String): String {
        val buf = StringBuilder()
        val json: InputStream?
        try {
           // json = assets.open(sPath)
            json = FileInputStream(File(sPath))
            val bffrIn = BufferedReader(InputStreamReader(json, "UTF-8"))
            var str: String? = null

            while ({ str = bffrIn.readLine(); str }() != null) {
                buf.append(str)
            }
            bffrIn.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return buf.toString()
    }
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}