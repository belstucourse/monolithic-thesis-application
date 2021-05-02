package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.updater.UserUpdater;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "support")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Support extends User<Support> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_admin_id")
    private Admin admin;

    @Override
    public Support update(final UserUpdater userUpdater, final Support newUser) {
        return userUpdater.update(this, newUser);
    }
}
