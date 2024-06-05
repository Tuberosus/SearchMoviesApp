package ru.me.searchmoviesapp.data.converters

import ru.me.searchmoviesapp.data.dto.full_cast.ActorShortDto
import ru.me.searchmoviesapp.data.dto.full_cast.CastShortDto
import ru.me.searchmoviesapp.data.dto.full_cast.CastShortItemDto
import ru.me.searchmoviesapp.data.dto.full_cast.FullCastResponse
import ru.me.searchmoviesapp.domain.models.FullCastData
import ru.me.searchmoviesapp.domain.models.MovieCastPerson

class MovieCastConverter {
    fun convert(response: FullCastResponse): FullCastData {
        return with(response) {
            FullCastData(
                imDbId = this.imDbId,
                title = this.title,
                directors = convertCastShort(this.directors),
                others = convertOthers(this.others),
                writers = convertCastShort(this.writers),
                actors = convertActorsShort(this.actors),
            )

        }
    }

    private fun convertOthers(others: List<CastShortDto>): List<MovieCastPerson> {
        return others.flatMap { other ->
            other.items.map { it.toMovieCastPerson(jobPrefix = other.job) }
        }
    }

    private fun convertCastShort(cast: CastShortDto): List<MovieCastPerson> {
        return cast.items.map { it.toMovieCastPerson() }
    }

    private fun convertActorsShort(actors: List<ActorShortDto>): List<MovieCastPerson> {
        return actors.map { actor ->
            MovieCastPerson(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image,
            )
        }
    }

    private fun CastShortItemDto.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            id = this.id,
            name = this.name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix - ${this.description}",
            image = null
        )
    }
}