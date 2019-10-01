package com.example.raghu.dagger2testandroid.api

/*
import com.example.raghu.dagger2testandroid.PostProcessable
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.IOException



class PostProcessingEnabler : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)

        return object : TypeAdapter<T>() {
            override fun write(out: com.google.gson.stream.JsonWriter?, value: T) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): T {
                val obj = delegate.read(`in`)
                if (obj is PostProcessable) {
                    (obj as PostProcessable).gsonPostProcess()
                }
                return obj
            }
        }
    }
}*/
