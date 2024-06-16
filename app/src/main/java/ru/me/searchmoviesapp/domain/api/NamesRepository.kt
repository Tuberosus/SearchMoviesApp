package ru.me.searchmoviesapp.domain.api

import ru.me.searchmoviesapp.domain.models.Name
import util.Resource

interface NamesRepository {
    fun searchName(expression: String): Resource<List<Name>>
}