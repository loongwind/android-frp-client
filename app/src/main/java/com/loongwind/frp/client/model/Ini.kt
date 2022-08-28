package com.loongwind.frp.client.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToMany


@Entity
data class IniProperty(@Id var id:Long = 0, var key:String, var value:String)

@Entity
data class IniSection(
    @Id var id: Long = 0,
    var name: String){
    lateinit var configs : ToMany<IniProperty>

    @Transient
    val isRunning = ObservableBoolean(false)

    @Transient
    val error = ObservableField<String>()

}


@Entity
data class IniConfig(
    @Id var id:Long = 0,
    var name:String = ""
){
    lateinit var sections: ToMany<IniSection>
}



