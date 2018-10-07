<template>

    <ul class="person-list">
        <li class="person-list__item" v-for="friend in friends">{{friend.text}}</li>
    </ul>

</template>

<script>
    export default {
        data: function () {
            return {
                friends: null
            }
        },
        created: function () {
            this.$http.get('/friends').then(response => {
                console.log(response);
                // get body data
                this.friends = response.body;

            }, response => {
                console.log("ОШИБКА!");
            })
        }

    }

</script>