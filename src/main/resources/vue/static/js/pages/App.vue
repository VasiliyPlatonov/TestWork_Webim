<template>
    <div>
        <navigation></navigation>

        <div class="main-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-xs-4 col-sm-3">

                        <friend-list></friend-list>

                    </div>

                    <div class="col-xs-8 col-sm-9">
                        <div class="container">
                            <div class="row">

                                <!--<friend-info></friend-info>-->

                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>

        <footer class="footer">
            <p>2018 developing by Vasiliy Platonov</p>
        </footer>

    </div>

</template>

<script>
    import Navigation from '../components/Navigation.vue'
    import FriendList from '../components/FriendList.vue'
    import friendInfo from '../components/FriendInfo.vue'

    export default {
        data: function () {
            return {
                message: 'Hello dfcz!',
                friends: null
            }
        },
        components: {
            navigation: Navigation,
            friendList: FriendList,
            friendInfo: friendInfo
        },
//        created: function () {
//            this.$http.get('/friends').then(response => {
//                console.log(response);
//                // get body data
//                this.friends = response.body;
//
//            }, response => {
//                console.log("ОШИБКА!");
//            })
//        }

    }

</script>

<style lang="sass"></style>

