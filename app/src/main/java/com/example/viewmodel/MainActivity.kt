package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.ui.theme.ViewModelTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelTheme {
                // viewModel()을 사용하여 ViewModel 인스턴스를 가져옴
                val viewModel: DiceRollViewModel = viewModel()
                // uiState를 observe하고 Composable에 전달
                val uiState by viewModel.uiState.collectAsState()

                DiceRollScreen(
                    uiState = uiState,
                    onRollDice = { viewModel.rollDice() }
                )
            }
        }
    }
}

data class DiceUiState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0
)

class DiceRollViewModel : ViewModel(){
    // UI 상태를 저장하는 MutableStateFlow
    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    // 비즈니스 로직: 주사위 굴리기
    fun rollDice(){
        _uiState.update { currentState ->
            currentState.copy(
                firstDieValue = Random.nextInt(from = 1, until = 7),
                secondDieValue = Random.nextInt(from = 1, until = 7),
                numberOfRolls = currentState.numberOfRolls + 1
            )
        }
    }
}

@Composable
fun DiceRollScreen(
    uiState: DiceUiState,
    onRollDice: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "First Die: ${uiState.firstDieValue ?: "-"}")
        Text(text = "Second Die: ${uiState.secondDieValue ?: "-"}")
        Text(text = "Number of Rolls: ${uiState.numberOfRolls}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRollDice) {
            Text("Roll Dice")
        }
    }
}

