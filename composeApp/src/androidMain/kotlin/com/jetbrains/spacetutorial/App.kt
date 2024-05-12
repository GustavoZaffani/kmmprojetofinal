package com.jetbrains.spacetutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetbrains.spacetutorial.constants.Constants.EMPTY
import com.jetbrains.spacetutorial.constants.TranslationConstants
import com.jetbrains.spacetutorial.dto.MedicoCreateDTO
import com.jetbrains.spacetutorial.dto.MedicoDTO
import com.jetbrains.spacetutorial.dto.MedicoUpdateDTO
import com.jetbrains.spacetutorial.enums.EnumEspecialidade
import com.jetbrains.spacetutorial.presentaction.MedicoViewModel
import com.jetbrains.spacetutorial.theme.AppTheme
import com.jetbrains.spacetutorial.ui.LabelCard
import com.jetbrains.spacetutorial.ui.Loader
import com.jetbrains.spacetutorial.ui.RadioButtonField
import com.jetbrains.spacetutorial.ui.StringField
import com.jetbrains.spacetutorial.ui.TitleCard
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun App() {
    val viewModel = koinViewModel<MedicoViewModel>()
    val state by remember { viewModel.state }
    val pullToRefreshState = rememberPullToRefreshState()

    var nome by remember { mutableStateOf(EMPTY) }
    var crm by remember { mutableStateOf(EMPTY) }
    var email by remember { mutableStateOf(EMPTY) }
    val (selectedOption, setSelectedOption) = remember { mutableStateOf(EnumEspecialidade.ORTOPEDIA.toString()) }

    var showDialog by remember { mutableStateOf(false) }
    var editId by remember { mutableLongStateOf(0L) }

    if (pullToRefreshState.isRefreshing) {
        viewModel.loadMedicos()
        pullToRefreshState.endRefresh()
    }

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        TranslationConstants.MEDICOS,
                        style = MaterialTheme.typography.headlineLarge
                    )
                })
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        editId = 0L
                        showDialog = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = TranslationConstants.NOVO
                    )
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .fillMaxSize()
                    .padding(padding),
            ) {
                if (state.isLoading) {
                    Loader()
                } else {
                    LazyColumn {
                        items(state.medicos) { medico: MedicoDTO ->
                            Column(modifier = Modifier.padding(all = 16.dp)) {
                                TitleCard(value = "${medico.especialidade} - ${medico.nome}")

                                Spacer(Modifier.height(8.dp))

                                LabelCard(label = TranslationConstants.EMAIL, value = medico.email)

                                Spacer(Modifier.height(8.dp))

                                LabelCard(label = TranslationConstants.CRM, value = medico.crm)

                                Spacer(Modifier.height(8.dp))

                                Button(
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                                    onClick = {
                                        editId = medico.id
                                        nome = medico.nome
                                        crm = medico.crm
                                        email = medico.email
                                        setSelectedOption(medico.especialidade)
                                        showDialog = true
                                    }
                                ) {
                                    Text(TranslationConstants.EDITAR)
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }

                PullToRefreshContainer(
                    state = pullToRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                if (showDialog) {
                    val isNew = editId == 0L

                    AlertDialog(
                        onDismissRequest = {
                            pullToRefreshState.startRefresh()
                            showDialog = false
                        },
                        title = {
                            Text(
                                if (!isNew) TranslationConstants.EDITAR_MEDICO else TranslationConstants.ADICIONAR_MEDICO,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        },
                        text = {
                            Column {
                                StringField(
                                    fieldValue = nome,
                                    fieldLabel = TranslationConstants.NOME,
                                    isRequired = true
                                ) { nome = it }

                                StringField(
                                    fieldValue = email,
                                    fieldLabel = TranslationConstants.EMAIL,
                                    isRequired = true,
                                    isEnabled = isNew
                                ) { email = it }

                                StringField(
                                    fieldValue = crm,
                                    fieldLabel = TranslationConstants.CRM,
                                    isRequired = true,
                                    isEnabled = isNew
                                ) { crm = it }

                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        RadioButtonField(
                                            radioValue = EnumEspecialidade.ORTOPEDIA.toString(),
                                            selectedOption = selectedOption,
                                            radioLabel = TranslationConstants.ORTOPEDIA,
                                            isEnabled = isNew
                                        ) { setSelectedOption(it) }

                                        RadioButtonField(
                                            radioValue = EnumEspecialidade.CARDIOLOGIA.toString(),
                                            selectedOption = selectedOption,
                                            radioLabel = TranslationConstants.CARDIOLOGIA,
                                            isEnabled = isNew
                                        ) { setSelectedOption(it) }
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        RadioButtonField(
                                            radioValue = EnumEspecialidade.GINECOLOGIA.toString(),
                                            selectedOption = selectedOption,
                                            radioLabel = TranslationConstants.GINECOLOGIA,
                                            isEnabled = isNew
                                        ) { setSelectedOption(it) }

                                        RadioButtonField(
                                            radioValue = EnumEspecialidade.DERMATOLOGIA.toString(),
                                            selectedOption = selectedOption,
                                            radioLabel = TranslationConstants.DERMATOLOGIA,
                                            isEnabled = isNew
                                        ) { setSelectedOption(it) }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (nome.isNotBlank() && crm.isNotBlank() && email.isNotBlank()) {
                                    if (!isNew) {
                                        val medico = MedicoUpdateDTO(
                                            id = editId,
                                            nome = nome
                                        )

                                        viewModel.updateMedico(medico)
                                    } else {
                                        val medico = MedicoCreateDTO(
                                            nome = nome,
                                            email = email,
                                            crm = crm,
                                            especialidade = selectedOption
                                        )

                                        viewModel.addMedico(medico)
                                    }

                                    nome = EMPTY
                                    crm = EMPTY
                                    email = EMPTY
                                    setSelectedOption(EnumEspecialidade.ORTOPEDIA.toString())
                                    showDialog = false
                                    pullToRefreshState.startRefresh()
                                }
                            }) {
                                Text(TranslationConstants.SALVAR)
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                showDialog = false
                                pullToRefreshState.startRefresh()
                            }) {
                                Text(TranslationConstants.CANCELAR)
                            }
                        }
                    )
                }
            }
        }
    }
}