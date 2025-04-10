const username = 'tester'; // ユーザー名を固定で'tester'に設定
const roleEl = document.getElementById('role');

// サーバーからユーザーの権限を取得
function updateRole() {
    fetch(`/role-info/${username}`, { cache: 'no-store' })  // キャッシュを無効化
        .then(response => response.text())
        .then(role => {
            roleEl.textContent = role; // ユーザーの権限を表示
        })
        .catch(error => {
            console.error("権限取得エラー:", error);
            roleEl.textContent = '権限情報の取得に失敗しました';
        });
}

// 初回の権限取得
updateRole();

// WebSocket接続
const socket = new SockJS('/ws'); // サーバー側のWebSocketエンドポイントに接続
const stompClient = Stomp.over(socket);

// WebSocket接続後にメッセージの購読を開始
stompClient.connect({}, () => {
    console.log("WebSocket接続成功");

    stompClient.subscribe(`/topic/role/${username}`, (message) => {
        const newRole = message.body; // メッセージのボディに新しい権限が入っている
        alert("権限が変更されました：" + newRole); // 権限変更の通知
        updateRole(); // WebSocket通知を受けたら再度権限をサーバーから取得して更新
    });
}, (error) => {
    console.error("WebSocket接続エラー:", error); // 接続エラーのログを表示
    alert("WebSocket接続エラー: " + error);
});
