# TWYN example app

TWYN example app é um aplicativo android contendo exemplo de chamada de registro e autenticação biométrica para uma determinada pessoa.


## Configuração
Incluir as seguintes dependências em seu arquivo build.gradle, nível do aplicativo


```
plugins {
    ...
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.5.31'
    ...
}
android {
    ...
    dataBinding {
        enabled true
    }
    ...


}


implementation 'com.squareup.okhttp3:okhttp:4.9.2'
implementation 'com.github.f0ris.sweetalert:library:1.6.2'
implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1'
implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.4'
implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
implementation "androidx.work:work-runtime-ktx:2.7.1"
implementation (files("path-to-aar-file"))
```

## Implementação
A implementação das chamadas é feita de forma assíncrona, cabendo ao desenvolvedor ficar responsável pela implementação da captura de evento.

Exemplo de captura de evento em uma Activity
```
private val pickCapture = registerForActivityResult(CaptureActivityContract()) { result ->
    Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
    result.toString()
}
```

Exemplo de captura de evento em uma Activity
```
private val pickCapture = registerForActivityResult(CaptureActivityContract()) { result ->
    Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
    result.toString()
}
```

Um objeto do tipo InputData deve ser implementado para a informação dos valores, como no exemplo

```
val input = InputData(
    document:String,
    authorizationCode: String,
    type: TransactionType,
    name: String,
    email: String,
    mobilePhone: String,
    dateOfBirth: String
)
```

Após instanciar objeto, executar a captura de informações
```
    pickCapture.launch(input)
```

Aguardar o retorno da transação