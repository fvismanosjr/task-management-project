import { Client } from '@stomp/stompjs';
import type { IMessage, StompSubscription } from '@stomp/stompjs';
import type {
    BoardType,
    TaskType,
    BoardResponseType
 } from '@/lib/types';

type AnyCallback<T> = (msg: T) => void;

class SocketService {
    private client: Client | null = null;
    private connected = false;

    // Subscriptions per channel
    private subscriptions: Map<string, StompSubscription> = new Map();

    connect(onConnect?: () => void): void {
        if (this.connected) return;

        this.client = new Client({
            brokerURL: "ws://localhost:8080/ws-task-management",
            reconnectDelay: 1000,
            debug: (msg: string) => console.log("[STOMP]", msg),
        });

        this.client.onConnect = () => {
            this.connected = true;
            console.log("✅ STOMP connected");
            onConnect?.();
        };

        this.client.onStompError = (frame) => {
            console.error("❌ STOMP error:", frame.headers['message']);
            console.error("Details:", frame.body);
        };

        this.client.onWebSocketClose = () => {
            this.connected = false;
            console.log("🔌 WebSocket closed");
        };

        this.client.activate();
    }

    // 🔥 Generic subscribe method
    private subscribeGeneric<T>(
        channel: string,
        destination: string,
        callback: AnyCallback<T>
    ): void {
        if (!this.client || !this.connected) {
            console.warn("Trying to subscribe before connection.");
            return;
        }

        if (this.subscriptions.has(channel)) {
            console.warn(`Already subscribed to ${channel}`);
            return;
        }

        const sub = this.client.subscribe(destination, (message: IMessage) => {
            try {
                const data = JSON.parse(message.body) as T;
                callback(data);
            } catch (err) {
                console.error("Failed to parse WS message:", err);
            }
        });

        this.subscriptions.set(channel, sub);
        console.log(`📡 Subscribed to ${destination}`);
    }

    // ---------------------------
    //  BOARD CHANNEL HELPERS
    // ---------------------------

    subscribeToBoard(boardId: number | string, callback: (updatedBoard: BoardResponseType) => void) {
        const channel = `board.${boardId}`;
        const destination = `/topic/board.${boardId}`;

        this.subscribeGeneric(channel, destination, callback);
    }

    sendBoardUpdate(boardId: number | string, board: BoardType) {
        if (!this.client || !this.connected) return;

        this.client.publish({
            destination: `/app/board/${boardId}`,
            body: JSON.stringify(board),
            headers: { 'content-type': 'application/json' },
        });
    }

    // ---------------------------
    //  TASK CHANNEL HELPERS
    // ---------------------------

    subscribeToTasks(boardId: number | string, callback: (newTasks: TaskType[]) => void) {
        const channel = `tasks.${boardId}`;
        const destination = `/topic/tasks.${boardId}`;

        this.subscribeGeneric(channel, destination, callback);
    }

    sendTaskUpdate(boardId: number | string, task: TaskType) {
        if (!this.client || !this.connected) return;

        this.client.publish({
            destination: `/app/task/${boardId}`,
            body: JSON.stringify(task),
            headers: { 'content-type': 'application/json' },
        });
    }

    subscribeToUserBoards(userId: number | string, callback: (boards: BoardType[]) => void) {
        const channel = `userBoards.${userId}`;
        const destination = `/topic/user.${userId}.boards`;

        this.subscribeGeneric(channel, destination, callback);
    }

    sendUserBoardsUpdate(userId: number | string, boards: BoardType[]) {
        if (!this.client || !this.connected) return;

        this.client.publish({
            destination: `/app/user/${userId}/boards`,
            body: JSON.stringify(boards),
            headers: { 'content-type': 'application/json' },
        });
    }

    // ---------------------------
    // Unsubscribe / Disconnect
    // ---------------------------

    unsubscribe(channel: string) {
        const sub = this.subscriptions.get(channel);
        if (sub) sub.unsubscribe();
        this.subscriptions.delete(channel);

        console.log(`🗑️ Unsubscribed from ${channel}`);
    }

    disconnect(): void {
        if (!this.client || !this.connected) return;

        this.client.deactivate();
        this.connected = false;
        this.subscriptions.clear();

        console.log("🔌 STOMP disconnected");
    }
}

export default new SocketService();
