const BOARD_API_URL = "http://localhost:8080/boards";

interface BoardRequest {
    name: string,
}

export const getBoards = async () => {
    return await fetch(BOARD_API_URL, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const findBoard = async (id: number) => {
    return await fetch(`${BOARD_API_URL}/${id}`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const saveBoard = async (board: BoardRequest) => {
    return await fetch(BOARD_API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(board),
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const updateBoard = async (id:number, board: BoardRequest) => {
    return await fetch(`${BOARD_API_URL}/${id}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(board),
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const deleteBoard = async (id: number) => {
    return await fetch(`${BOARD_API_URL}/${id}`, {
        method: "DELETE",
        credentials: "include",
    });
}
