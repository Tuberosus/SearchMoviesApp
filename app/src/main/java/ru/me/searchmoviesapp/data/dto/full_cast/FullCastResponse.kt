package ru.me.searchmoviesapp.data.dto.full_cast

import ru.me.searchmoviesapp.data.dto.movies.Response

class FullCastResponse(val imDbId: String,
                       val title: String,
                       val fullTitle: String,
                       val type: String,
                       val year: String,
                       val directors: CastShortDto,
                       val writers: CastShortDto,
                       val actors: List<ActorShortDto>,
                       val others: List<CastShortDto>,
                       val errorMessage: String,) : Response()