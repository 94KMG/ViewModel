# DiceRollApp README

## 프로젝트 개요
이 프로젝트는 Jetpack Compose를 활용한 간단한 주사위 굴리기 앱입니다. ViewModel을 통해 UI 상태를 관리하고, 상태 변화에 따라 UI가 자동으로 업데이트되도록 설계되었습니다.

## 주요 개념

### 1. ViewModel과 데이터 상태 관리
- **ViewModel**은 UI와 독립적으로 **상태를 유지**하는 데 사용됩니다. 화면 회전 등으로 액티비티가 재생성되더라도 상태를 잃지 않게 합니다.
- **데이터 클래스**(예: `DiceUiState`)는 ViewModel에서 추적하는 **상태의 기본 단위**로 사용됩니다. 주사위 값과 굴린 횟수 같은 상태를 관리합니다.

### 2. MutableStateFlow와 StateFlow
- **MutableStateFlow**는 **변경 가능한 상태 흐름**을 나타냅니다. ViewModel 내부에서 상태를 업데이트할 때 사용됩니다.
- **StateFlow**는 **읽기 전용 상태 흐름**으로, UI에서는 상태를 관찰만 할 수 있고 직접 수정하지는 못합니다.
- 두 변수의 관계는, `MutableStateFlow`는 **내부적으로 상태를 변경**하고, `StateFlow`는 **외부에 읽기 전용으로 노출**하는 역할을 합니다.

### 3. collectAsState()의 역할
- **collectAsState()**는 `StateFlow`를 **Compose의 State로 변환**하는 함수입니다. 이를 통해 UI는 상태가 변경될 때마다 **자동으로 리컴포지션(재구성)** 됩니다.
- `StateFlow`는 시간이 지나면서 상태가 **연속적으로 변경**되는 데이터 흐름을 다루고, 이를 Compose에서 직접 사용하려면 `State`로 변환해야 합니다.

### 4. val과 MutableStateFlow의 관계
- **`val`**로 선언된 변수는 **참조 자체는 불변**이지만, 그 참조 대상인 **객체의 내부 상태는 변경될 수 있습니다**.
- 예를 들어, `val _uiState = MutableStateFlow(DiceUiState())`에서 `_uiState`는 재할당할 수 없지만, `MutableStateFlow` 내부의 값은 변경 가능합니다.

## ViewModel의 추가적인 역할
- ViewModel은 단순히 상태를 추적하는 것 외에도 **비즈니스 로직 처리**, **네트워크 요청** 및 **데이터베이스 작업** 등 다양한 역할을 수행할 수 있습니다.
- ViewModel에서 UI 이벤트를 처리하여 **상태 변화**를 UI에 반영하고, **데이터 요청**과 같은 비동기 작업도 수행할 수 있습니다.

## 의존성 추가 (필수)
Jetpack Compose에서 ViewModel을 사용하려면 아래와 같은 의존성을 **`build.gradle`** 파일에 추가해야 합니다.

```gradle
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
}
