<script setup lang="ts">
import {
    Avatar,
    AvatarFallback,
} from '@/components/ui/avatar'

import {
    Empty,
    EmptyDescription,
    EmptyHeader,
    EmptyMedia,
    EmptyTitle,
} from '@/components/ui/empty'

import {
    Item,
    ItemActions,
    ItemContent,
    ItemTitle,
} from '@/components/ui/item'

import { Button } from '@/components/ui/button'
import { ChevronRightIcon, LayoutList, Pencil } from 'lucide-vue-next'
import ConfirmDialog from '@/components/ConfirmDialog.vue'
import type { BoardType } from '@/lib/types';
import { getBoards } from '@/services/board';
import { initials } from '@/helpers/StringHelper';
import { useUserStore } from "@/stores/user"
import { findMember } from '@/services/boardMember'
import SocketService from '@/services/socket'
import { onUnmounted, ref } from 'vue';

const user = useUserStore();
const emit = defineEmits<{
    (e: "update:open-dialog", value: number): void
}>();

const confirmDialogKey = ref(0);
const boards = ref<BoardType[]>([]);

if (user.isOwner) {
    getBoards().then((response) => {
        boards.value = response;
    })
} else {
    findMember(user.user.id).then((response) => {
        boards.value = response.boards;

        SocketService.connect(() => {
            SocketService.subscribeToUserBoards(user.user.id, (newBoards: BoardType[]) => {
                boards.value = newBoards;
            });
        });
    })
}

const openBoardDialog = (id: number) => {
    emit("update:open-dialog", id);
}

const refresh = async (val: boolean) => {
    if (val) {
        boards.value = [];

        await getBoards().then((response) => {
            boards.value = response;
        });

        confirmDialogKey.value++;
    }
}

onUnmounted(() => {
    SocketService.disconnect();
})
</script>

<template>
    <div class="flex w-full flex-col gap-3">
        <template v-for="board in boards" :key="`board-${board.id}`">
            <Item variant="outline">
                <ItemContent>
                    <ItemTitle>{{ board.name }}</ItemTitle>
                    <template v-if="board.members && board.members.length">
                        <div class="*:data-[slot=avatar]:ring-background flex -space-x-2 *:data-[slot=avatar]:ring-2 *:data-[slot=avatar]:grayscale">
                            <template v-for="member in board.members.slice(0, 3)" :key="`member-${member.username}`">
                                <Avatar>
                                    <AvatarFallback>{{ initials(member.username) }}</AvatarFallback>
                                </Avatar>
                            </template>
                        </div>
                    </template>
                </ItemContent>
                <ItemActions>
                    <template v-if="user.isOwner">
                        <Button variant="outline" size="icon-sm" @click.prevent="openBoardDialog(board.id)">
                            <Pencil />
                        </Button>
                        <ConfirmDialog
                            :id="board.id"
                            :type="'board'"
                            :key="`confirm-dialog-${board.id}-${confirmDialogKey}`"
                            @reload-component="refresh"
                        />
                    </template>
                    <Button variant="outline" size="icon-sm" @click.prevent="$router.push({ name: 'board-profile', params: { id: board.id } })">
                        <ChevronRightIcon />
                    </Button>
                </ItemActions>
            </Item>
        </template>
        <Empty v-if="!boards.length">
            <EmptyHeader>
                <EmptyMedia variant="icon">
                    <LayoutList />
                </EmptyMedia>
            </EmptyHeader>
            <EmptyTitle>No Boards Yet</EmptyTitle>
            <EmptyDescription>You’ll need a board first! Ask the creator to set one up.</EmptyDescription>
        </Empty>
    </div>
</template>
