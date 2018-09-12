package pl.com.altar.dddlayerd.domain.base;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Data
public abstract class EntityBase<T extends EntityBase<T>> {

    private static final String IDENTITY_MISSING_IN_ENTITY = "Identity missing in entity: ";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;



    public boolean sameIdentityAs(final T that){
        return this.equals(that);
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof EntityBase)) {
            return false;
        }
        final EntityBase<?> that = (EntityBase<?>) object;
        checkIdentity(this);
        checkIdentity(that);
        return this.id.equals(that.getId());
    }


    private void checkIdentity(final EntityBase<?> entity) {
        if(entity.getId()==null){
            throw new IllegalStateException(IDENTITY_MISSING_IN_ENTITY + entity);
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
