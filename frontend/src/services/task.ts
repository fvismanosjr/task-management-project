const BOARD_API_URL = `http://localhost:8080/boards`;

interface TaskRequest {
    title: string,
    assignee: number,
    comment?: string,
}

export const getTasks = async (id: number) => {
    return await fetch(`${BOARD_API_URL}/${id}/tasks`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const findTask = async (id: number, taskId: number) => {
    return await fetch(`${BOARD_API_URL}/${id}/tasks/${taskId}`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

// id here is the board id
export const saveTask = async (id: number, task: TaskRequest) => {
    return await fetch(`${BOARD_API_URL}/${id}/tasks`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(task),
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const updateTask = async (id: number, taskId: number, task: TaskRequest) => {
    return await fetch(`${BOARD_API_URL}/${id}/tasks/${taskId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(task),
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const deleteTask = async (id: number, taskId: number) => {
    return await fetch(`${BOARD_API_URL}/${id}/tasks/${taskId}`, {
        method: "DELETE",
        credentials: "include",
    });
}
