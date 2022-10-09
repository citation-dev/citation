package dev.m2en.citation.utils

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions

class Permission {

    companion object {

        /**
         * 指定されたメンバーが危険な権限を持っているかどうか確認します。
         * 危険な権限として指定されている権限は以下の通りです。
         * 　- Administrator
         * 　- ManageGuild
         * @param member メンバー
         * @return 指定されたメンバーが指定された権限を持っているかどうか
         */
        fun isDangerPermission(memberPermission: Permissions): Boolean {
            if(memberPermission.contains(Permission.Administrator) || memberPermission.contains(Permission.ManageGuild)) {
                return true
            }
            return false
        }
    }
}
