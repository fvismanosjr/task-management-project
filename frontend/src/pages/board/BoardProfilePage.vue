<script setup lang="ts">
import {
    Avatar,
    AvatarFallback,
    AvatarImage,
} from '@/components/ui/avatar'

import AppHeader from '@/components/AppHeader.vue';
import TaskList from '@/components/TaskList.vue';

// get board details (name, members, etc) and add it in the header using slots
</script>

<template>
    <AppHeader>
        <template #board-name>
            Sample Board Name
        </template>
        <template #board-member>
            <div class="*:data-[slot=avatar]:ring-background flex -space-x-2 *:data-[slot=avatar]:ring-2 *:data-[slot=avatar]:grayscale">
                <Avatar>
                    <AvatarImage src="https://github.com/shadcn.png" alt="@shadcn" />
                    <AvatarFallback>CN</AvatarFallback>
                </Avatar>
                <Avatar>
                    <AvatarImage src="https://github.com/leerob.png" alt="@leerob" />
                    <AvatarFallback>LR</AvatarFallback>
                </Avatar>
                <Avatar>
                    <AvatarImage
                    src="https://github.com/evilrabbit.png"
                    alt="@evilrabbit"
                    />
                    <AvatarFallback>ER</AvatarFallback>
                </Avatar>
            </div>
        </template>
    </AppHeader>
    <TaskList />
</template>
