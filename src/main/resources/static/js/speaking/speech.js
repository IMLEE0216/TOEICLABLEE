    var SetTime = 10;		// 최초 설정 시간(기본 : 초)

function msg_time() {	// 1초씩 카운트
    // m = Math.floor(SetTime / 60) + "분 " + (SetTime % 60) + "초";
    m = Math.floor(SetTime % 60) + "초";	// 남은 시간 계산

    var msg = "현재 남은 시간은 <font color='red'>" + m + "</font> 입니다.";

    document.all.ViewTimer.innerHTML = msg;		// div 영역에 보여줌

    SetTime--;					// 1초씩 감소

    if (SetTime < 0) {			// 시간이 종료 되었으면..

    clearInterval(tid);		// 타이머 해제
    alert("종료");
    }

}

$(document).ready(function(){
    $("#speech-btn").click(function (){
        TimerStart();
    });
});

function TimerStart(){
    tid=setInterval('msg_time()',1000)
}

$(document).ready(function(){
    $("#speech-btn").click(function(){
        $.ajax({
            type: 'GET',
            url: '/speech',
            dataType: 'json',
            success: function (data) {
                var speech = JSON.stringify(data.result);
                // $('input[name=speech]').attr('value', speech);
                document.getElementById('speech-textarea').innerHTML = speech;
            },
            error: function(){
                alert("다시 시도해주시기 바랍니다.")
            }

        })
    });
});