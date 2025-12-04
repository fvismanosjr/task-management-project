export interface UserType {
    id: number,
    username: string,
    role: string,
}

export interface BoardType {
    id: number,
    name: string,
    members?: BoardMemberType[]
}

export interface BoardWithMembersType {
    id: number,
    name: string,
    members: string[],
}

export interface TaskType {
    id: number,
    title: string,
    boardId: number,
}

export interface BoardMemberType {
    id: number,
    username: string,
}

export interface TaskResponseType {
    id: number,
    title: string,
    comment?: string,
    assignee?: BoardMemberType,
}

export interface BoardResponseType {
    id: number,
    name: string,
    members: BoardMemberType[],
    tasks: TaskResponseType[],
}
