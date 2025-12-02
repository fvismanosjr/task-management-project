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
import { ref } from 'vue';

const members = ref([
    {
        id: 1,
        username: "fvismanos"
    }
])
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
