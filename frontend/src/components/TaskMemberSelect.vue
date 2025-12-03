<script setup lang="ts">
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import { findBoard } from '@/services/board';
import { useRoute } from 'vue-router';
import { ref } from 'vue';

const route = useRoute();
const members = ref([
    {
        id: 0,
        username: ""
    }
])

findBoard(Number(route.params.id)).then((response) => {
    members.value = response.members;
})
</script>

<template>
    <Select>
        <SelectTrigger class="w-full">
            <SelectValue placeholder="Select a member" />
        </SelectTrigger>
        <SelectContent>
            <SelectGroup>
                <SelectLabel>Members</SelectLabel>
                <template v-for="member in members" :key="`member-${member.id}`">
                    <SelectItem :value="member.id">
                        {{ member.username }}
                    </SelectItem>
                </template>
            </SelectGroup>
        </SelectContent>
    </Select>
</template>
